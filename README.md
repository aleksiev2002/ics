# TagWizard - Image Classification Service

TagWizard is an Image Classification Service (ICS) that allows users to submit image URLs and receive tags (categorization) based on the content of the images. 
The service utilizes online image recognition services to perform the analysis.

## Overview

TagWizard is a web-based application that provides an intuitive user interface for submitting and retrieving image analysis results. 
The application consists of a back-end server, a web API, and a web-based user interface with three main pages: image submission, image viewing, and image gallery. 
The back-end server handles requests, connects to the chosen image recognition service, stores analysis results in a database, 
and enables communication with the front-end through the API.

## Features

The TagWizard app includes the following features:

- Web-based user interface with three pages:
  - Submit images for analysis
  - View analyzed images
  - Gallery of uploaded images
- Web API for submitting and retrieving images
- Back-end server to connect to recognition services, perform analysis, store data, and handle requests
- Continuous Integration (CI) pipeline for building, testing, and analyzing the codebase

