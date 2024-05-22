import time
from locust import FastHttpUser, task, between
import random
import string

chars = string.ascii_letters + string.digits

class QuickstartUser(FastHttpUser):
    wait_time = between(1, 5)

    @task
    def hello_world(self):
        clientName = ''.join(random.choices(chars, k=15))
        freeName = ''.join(random.choices(chars, k=15))

        self.client.post("/api/v1/users/clients/register", json={"email": clientName, "password": freeName, "clientName": "3beet"})
        self.client.post("/api/v1/users/freelancers/register", json={"email": freeName, "password": clientName, "fullName": "3beet eltany"})

        resp = self.client.post("/api/v1/users/login", json={"email": clientName, "password": freeName})
        clientToken = resp.json()["authToken"]

        resp = self.client.post("/api/v1/users/login", json={"email": freeName, "password": clientName})
        freeToken = resp.json()["authToken"]

        resp = self.client.post("/api/v1/jobs", json={"title": "3beet", "description": "3beet eltany"}, headers={"Authorization": f"Bearer {clientToken}"})
        jobId = resp.json()["jobId"]

        self.client.get(f"/api/v1/jobs/{jobId}", name="/api/v1/jobs/[jobId]", headers={"Authorization": f"Bearer {freeToken}"})

        resp = self.client.post(f"/api/v1/jobs/{jobId}/proposals", name="/api/v1/jobs/[jobId]/proposals", json={
            "jobDuration":"LESS_THAN_A_MONTH",
            "coverLetter":"hirre mee",
            "milestones":[{
                    "description":"Create backend",
                    "amount":100,
                    "dueDate":"2024-09-09"
                }
            ]
        }, headers={"Authorization": f"Bearer {freeToken}"})
        proposalId = resp.json()["id"]

        resp = self.client.post(f"/api/v1/jobs/{jobId}/proposals/{proposalId}/accept", name="/api/v1/jobs/[jobId]/proposals/[proposalId]/accept", headers={"Authorization": f"Bearer {clientToken}"})
        contractId = resp.json()["contractId"]

        resp = self.client.get(f"/api/v1/contracts/{contractId}", name="/api/v1/contracts/[contractId]", headers={"Authorization": f"Bearer {clientToken}"})
        milestoneId = resp.json()["milestonesIds"][0]

        self.client.post(f"/api/v1/contracts/milestones/{milestoneId}/progress", name="/api/v1/contracts/milestones/[milestoneId]/progress", headers={"Authorization": f"Bearer {freeToken}"}, json={})
        self.client.post(f"/api/v1/contracts/milestones/{milestoneId}/progress", name="/api/v1/contracts/milestones/[milestoneId]/progress", headers={"Authorization": f"Bearer {freeToken}"}, json={})

        self.client.post(f"/api/v1/contracts/milestones/{milestoneId}/evaluate", name="/api/v1/contracts/milestones/[milestoneId]/evaluate", headers={"Authorization": f"Bearer {clientToken}"}, json={
            "evaluatedState": "ACCEPTED" 
        })

        resp = self.client.get("/api/v1/payments/clients/me/requests", headers={"Authorization": f"Bearer {clientToken}"})
        paymentId = resp.json()["requests"][0]["id"]

        self.client.post(f"/api/v1/payments/requests/{paymentId}/pay", name="/api/v1/payments/requests/[paymentId]/pay", headers={"Authorization": f"Bearer {clientToken}"})

        self.client.post("/api/v1/payments/freelancers/me/wallet/withdraw", headers={"Authorization": f"Bearer {freeToken}"}, json={
            "amount": 50,
        })

        resp = self.client.get("/api/v1/payments/freelancers/me/wallet", headers={"Authorization": f"Bearer {freeToken}"})
