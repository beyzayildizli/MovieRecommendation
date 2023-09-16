# MovieRecommendation
This is a program that provides recommendations according to the target user and movies and also my data structures lesson project.

## Screenshots
Here's a glimpse of the project:
<div style="display: flex; justify-content: center;">
  <img src="https://github.com/beyzayildizli/MovieRecommendation/assets/77398074/bf19cbda-05df-49ca-bb84-51ee286d5dcd" alt="d1" width="30%">
  <img src="https://github.com/beyzayildizli/MovieRecommendation/assets/77398074/460e5655-0d6d-40cf-a452-57031424b9f3" alt="d2" width="30%">
  <img src="https://github.com/beyzayildizli/MovieRecommendation/assets/77398074/88de249f-4b15-4110-bc61-a0ca938f12d5" alt="d3" width="30%">
</div>

## GET RECOMMENDATIONS ACCORDING TO THE TARGET USER:
1. In the first screen, the user only selects the target user from combo box
2. After this selection, our algorithm computes the similarity between each pair of users in the “main_data.csv” file and the target user using cosine similarity.
3. Then, We stored stored each user in a heap according to their similarity to the target user.
4. Finally, we get the most similar X user from the heap and list K highest rated movies for these users.

## GET RECOMMENDATION ACCORDING TO THE MOVIES:
1. We created a vector based on the movie the user selected and the amount of ratings that user gave
2. We computed the similarities between each pair of users in the “main_data.csv” file and user vector using cosine similarity.
3. Then, we stored each user in the heap according to their
similarity to the target user.
4. We got the most similar X user from the heap and list K highest rated movies for these users. In the total, we retrieved X * K movies.
5. Finally we listed the movie names.

## WHAT DID WE LEARN WHILE DOING THE PROJECT
- We learned when and why we have to use heaps and other data structures.
- Learned different algorithms that helps us to finish project.
- Now we can use generic data type more easy. It makes our codes more clean.
- We realized that we should be more careful while reading datas from file. Because sometimes there are some different datas that we should be careful while reading.

## HOW IS OUR CODE IMPLEMENTATION?
We used 8 different classes in our project. These:
### 1. CosMaxHeap.java:
This is a heap class created according to cosine smilarity. We performed operations such as adding and deleting ids by using cosine smilarity.
#### Methods in this class:
- **parent:**
It is used to calculate the parent node of a given node in a max heap.
- **swap:**
In an array, it replaces two elements whose index is given.
- **insert:**
Retrieves the score matrix of the selected user using the given targetMatrix and selectedId information. It adds this score matrix to the heap by comparing the score matrix of the user with the newId id according to cosine smilarity.
- **topDownHeapify:**
topDownHeapify is used to rearrange the heap after the element is deleted from the heap. Retrieves the score matrix of the selected user using the given targetMatrix and selectedId information. It rearranges the heap by comparing this score matrix with the score matrix of the user with the newId id according to cosine smilarity.
- **deleteMax:**
It deletes the root element of the heap and rearrange the heap by calling the topDownHeapify function.
- **cosineSimilarity:**
It calculates the similarity between two different arrays by formulating it over the cosine function in trigonometry.

### 2. FileOperations.java:
csv file operations and operations related to matrices created from these files are performed in this class.
#### Methods in this class:
- **fillHeapWithUserId:**
 filled the heap with the ids from the main_data.csv file
- **readFirstColumn:**
skips the first line of the given csv file and collects the value in the first column for all other lines in a list.
- **rowNumber:**
Returns the number of row of the csv file whose path is given
- **columnNumber:**
Returns the number of columns of the csv file whose path is given
- **matrixToArray:**
 It finds the id value given in the matrix and returns the row after the id.
- **targetUserMatrix:**
 We keep the target_user.csv file in the 2D matrix
- **rateMatrix:**
We keep the main_data.csv file in the 2D matrix
- **csvTo2DMatrix:**
- T[][] csvTo2DMatrix(String filePath, Class<T> tClass): Keeps the csv file given the file path in the 2D matrix, skipping the first line, and returns this matrix
- String csvTo2DMatrix(String filePath): Skips the first line of the csv file with the path given, keeps the comma separated data in the 2D matrix and returns this matrix. When separating the data by commas, if there is a part taken with quotation marks, it is the comma in the name of the movie and it does not use the commas in this part as a separator.
- **createTypedArray:**
Used to create a returned one-dimensional array of the requested type.
- **createTypedArray2D:**
It is used to create a returned 2-dimensional array of the requested type.

### 3. Operations.java:
This class has various sorting operations.
#### Methods in this class:
- **closeUsers:**
 Collects and returns a list of userNumber max elements from the CosMaxHeap named m sorted by the given cosine similarity.
- **bestMovies:**
Collects movie IDs as many as movieNum for selectedUserMatrix and returns this list.
- **movieNamesArray:**
It collects the names and genres of movies with given IDs in a list and returns this list.

### 4. GenericCast.java: 
Cast operations used with generics are in this class.
#### Methods in this class:
- **convertGenericToInt:**
Converts the given generic value to an integer.
- **convertGenericToString:**
Converts the given generic value to String.
- **convertToType:**
Makes the given String value generic in the desired class.

### 5. FirstPage.java:
This is the class in which our code runs. In this class, it is possible to go to the frames that created for the two suggestion stages requested from us via two buttons
#### Methods in this class:
- **targetButtonActionPerformed:**
Opens TargetUser frame.
- **moviesButtonActionPerformed:**
Opens the Movies frame

### 6. TargetUser.java:
We see the movie suggestions with the id selected according to the Target User in the gui in this class.
#### Methods in this class:
- **populateComboBoxFromCSV:**
Adds the ids of the csv file whose path is given to the comboBox userNumSpinnerStateChanged: When the value in userNumSpinne changes, it makes the new value of userNum variable the value in spinner.
- **movieSpinnerStateChanged:**  
When the value in movieSpinner changes, it changes the new value of movieNum to the value in the spinner.
- **recommend:** 
It creates a heap by taking advantage of the cos similarity between targetUserMatrix and mainMatrix. It takes the desired number of max users from the resulting heap and adds the desired number of movies to a list according to these users. Finally, jList prints this list.
- **backkButtonActionPerformed:** 
Returns to the page named as FirstPage.

### 7. Movies.java:
We see the movie suggestions made according to the 5 movies selected by the user in the gui in this class.
#### Methods in this class:
- **choose10Movie:**  
It randomly selects 10 movies from movie matrix and adds them to a new array with their ids.
- **populateComboBoxs:**  
Adds the names of 10 randomly selected movies to the comboBoxes.
- **findID:**  
It traverses the 2d array named Matrix and returns the id in this line if there is the same expression as the entered movie name by converting it to int.
- **newRateList:**  
Creates a new score array based on movie scores given in comboBoxes.
- **newTargetMatrix:**  
Adds id to the beginning of the given movie rate array. Then a new matrix is returned by adding this new array below the targetMatrix
- **recomment:**  
It creates a new targetMatrix with the matrix with the given movie scores and creates a heap by taking advantage of the cos similarity between this matrix and the mainMatrix. It takes the desired number of max users from the resulting heap and adds the desired number of movies to a list according to these users. Finally, jList prints this list.
- **checkSameElements:**  
Checks whether the same elements exist in the given integer array. Returns false if the same element exists
- **recommendButtonActionPerformed:**  
Collects selected movie ids in comboBoxes into an array. If the same movie is selected, it gives a warning. If all 5 movies are different, it recommends movies based on the new movie matrix created with newRateList().
- **userNumSpinnerStateChanged:**  
When the value in userNumSpinne changes, it makes the new value of userNum variable the value in spinner.
- **movieSpinnerStateChanged:**  
When the value in movieSpinner changes, it changes the new value of movieNum to the value in the spinner.
- **backButtonActionPerformed:**  
Returns to the page named FirstPage.

### 8. MyButton.java:
We added this button class to use a button that we customize as we want in the design.

## Getting Started
To experience the program on your local machine, follow these steps:

1. Clone this repository: `git clone https://github.com/beyzayildizli/MovieRecommendation.git`
2. Open the project in Unity.
3. Explore the project, playtest the program, and provide feedback!

## Contribution
Contributions to this project are highly welcome! If you have ideas, bug reports, or enhancements, feel free to open an issue or submit a pull request. Let's collaborate to make MovieRecommendation even better.

## Credits
This project was developed by Beyza Yıldızlı and Merve Öğ.
You can find us on [LinkedIn](https://www.linkedin.com/in/beyzayildizli/) or [GitHub](https://github.com/beyzayildizli) and [LinkedIn](https://www.linkedin.com/in/merve-og/) or [GitHub](https://github.com/MerveOg)
