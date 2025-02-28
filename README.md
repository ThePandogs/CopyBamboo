# CopyBamboo

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

A Java application built with Maven that organizes files based on their creation date, metadata creation date, last modification date, and file type. It also provides options to rename files with the date, create a folder for unclassified files, and overwrite existing files.

## The Story Behind This Project

This project was born out of a personal need. I had an iPhone full of photos that I couldn't transfer to my PC because the device kept freezing during the process. As a workaround, I had to download the photos directly from iCloud, but this method caused me to lose the organized folder structure I had on my phone. Frustrated by this, I decided to create a tool that could automatically organize files by date and type. Over time, I added more functionality to make it even more useful.

## Features

- **Classification by Date**:
  - Creation Date
  - Metadata Creation Date
  - Last Modification Date

- **Classification by File Type**:
  - Images, Videos, Documents, etc.

- **Additional Parameters**:
  - Rename files with the date
  - Generate a folder for unclassified files
  - Overwrite existing files

## Requirements

- Java JDK 8 or higher
- Apache Maven

### Installation

1. Clone the repository:

```bash
git clone https://github.com/ThePandogs/CopyBamboo
```

2. Compile the project and enjoy:
 ```bash
cd CopyBamboo

#Linux
 .\buildWindows.sh
#Windows
 .\buildWindows.bat
```
