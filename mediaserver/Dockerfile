# Use latest slim node
FROM node:current-slim

# Set the working directory
WORKDIR /www

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install modules for production
RUN npm install --production

# Copy entire bundle to the working directory
COPY . .

# Create a mount point marked as containing externally mounted volumes.
# Prevents the container's size from increasing with the addition of more static media assets.
# Can be overridden by binding a host directory at runtime.
VOLUME /www/media/static

# Override and expose port 8080
ENV PORT 8080
EXPOSE 8080

# Start the server
CMD ["npm", "run", "start"]