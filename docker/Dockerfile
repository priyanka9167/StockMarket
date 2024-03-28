# Use an official Node.js runtime as a parent image
FROM node:14

# Set the working directory in the container
WORKDIR /node_project

# Copy package.json and package-lock.json to the working directory
COPY package.json .
COPY package-lock.json .


# Install Node.js dependencies
RUN npm install

# Copy the rest of the application code to the working directory
COPY ./ ./


# Expose port 3000 to the outside world
EXPOSE 3000

# Command to run the Node.js application using npm start
CMD ["npm", "start"]


