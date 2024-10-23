# Use the official Node.js image as the base image
FROM node:18-alpine AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the package.json and package-lock.json to install dependencies
COPY package*.json ./

# Install the dependencies
RUN npm install

# Copy the rest of the application code into the container
COPY . .

# Build the React app for production
RUN npm run build

# Expose port 3000 (the default port for React)
# EXPOSE 5173

# CMD ["npm", "run", "dev", "--", "--host"]

# Stage 2: Serve the app using Nginx
FROM nginx:alpine

# Copy the built files from the previous stage
COPY --from=builder /app/dist /app/dist

# Expose port 5173 for the Nginx server
EXPOSE 5173

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
