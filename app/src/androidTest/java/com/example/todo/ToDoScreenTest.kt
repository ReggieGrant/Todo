package com.example.todo

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.todo.ui.screens.TodoScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.theories.suppliers.TestedOn

class ToDoScreenTest {
    // This rule launches the composable in a test environment
    @get:Rule
    val composeTestRule = createComposeRule()

    // A fresh instance of the view model is created before each test
    private lateinit var _viewModel: TodoViewModel

    @Before
    fun setUp() {
        _viewModel = TodoViewModel()
        // set the content to display -- same as setContent{} from main activity
        composeTestRule.setContent {
            TodoScreen(viewModel = _viewModel)
        }
    }

    @Test
    fun addTask_userTypesAndClicksAdd_taskAppearsInList() {
        // Arrange - the setUp() already created a fresh TodoScreen
        // verify the task does not exist in the list
        composeTestRule
            .onNodeWithText("Buy Groceries")
            .assertDoesNotExist()

        //Act - find the input field by its test tag and enter the text
        composeTestRule
            .onNodeWithTag("inputText")
            .performTextInput("Buy Groceries")

        //Act - find the button by its test tag and click it
        composeTestRule
            .onNodeWithTag("addButton")
            .performClick()

        //Assert - verify the task exists in the list
        // Challenge test the task is added to the list
        composeTestRule
            .onNodeWithText("Buy Groceries")
            .assertIsDisplayed()

    }
    @Test
    fun addTask_withBlankInput_taskDoesNotAppear(){
        // typing only spaces should not add task
        composeTestRule
            .onNodeWithTag("inputText")
            .performTextInput("   ")
        composeTestRule
            .onNodeWithTag("addButton")
            .performClick()
        // The task counter should not have changed and still be 0
        composeTestRule
            .onNodeWithText("0 tasks")
            .assertIsDisplayed()

    }
    @Test
    fun addTask_inputFieldClearsAfterAdding(){
        composeTestRule
            .onNodeWithTag("inputText")
            .performTextInput("Buy Groceries")
        // the input field should be empty after the task is added
        composeTestRule
            .onNodeWithTag("inputText")
            .assert(hasText("Enter a Task"))
    }
    // Challenge taskCounter updates when a task is added
    @Test
    fun addTask_taskCounterUpdates(){
        composeTestRule
            .onNodeWithTag("inputText")
            .performTextInput("Buy Groceries")
        composeTestRule
            .onNodeWithTag("addButton")
            .performClick()
        composeTestRule
            .onNodeWithText("1 tasks")
            .assertIsDisplayed()
    }

}
