# Step 1: Use a node image to build the project
FROM node:18-alpine as build

# Set the working directory
WORKDIR /app

# Copy the package.json and package-lock.json files
COPY package*.json ./

# Install the dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the React app with Vite
RUN npm run build

# Step 2: Use a lightweight web server (like Nginx) to serve the app
FROM nginx:alpine

# Copy the build folder from the previous build stage
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 80 to be able to access the app
EXPOSE 80

# Start Nginx to serve the app
CMD ["nginx", "-g", "daemon off;"]
