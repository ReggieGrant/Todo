package com.example.todo

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class TodoViewModelTest {
    // The system under test - recreated fresh before each test
    private lateinit var _viewModel: TodoViewModel

    @Before
    fun setup() {
        _viewModel = TodoViewModel()
    }

    @Test
    fun addTask_withValidTitle_appearsInList() {
        // Arrange - the setUp() already created an empty viewModel

        //Act - call the function being tested
        _viewModel.addTask("Buy Groceries")

        //Assert - check the result
        assertEquals(
            1, _viewModel.getTaskCount()
        )
        assertTrue(
            _viewModel.containsTask("Buy Groceries")
        )
    }

    @Test
    fun addTask_withBlankTitle_isIgnored() {
        _viewModel.addTask("   ")
        assertEquals(
            0, _viewModel.getTaskCount()
        )
    }

    // Challenge test the empty string case
    @Test
    fun addTask_withEmptyTitle_isIgnored() {
        _viewModel.addTask("")
        assertEquals(
            0, _viewModel.getTaskCount()
        )
    }

    // Challenge test the Trimmed string case
    @Test
    fun addTask_withTrimmedTitle_appearsInList() {
        _viewModel.addTask("   Buy Groceries   ")
        assertTrue(
            _viewModel.containsTask("Buy Groceries")
        )
    }

    @Test
    fun removeTask_removesTaskFromList() {
        // Arrange - add a task to the list
        _viewModel.addTask("Buy Groceries")
        val taskId = _viewModel.tasks[0].id

        //Act - call the function being tested
        _viewModel.removeTask(taskId)

        //Assert - check the result

        assertFalse(_viewModel.containsTask("Buy Groceries"))

    }
    @Test
    fun removeTask_renovesCorrectTask_whenMultipleExist() {
        // Arrange - add multiple tasks to the list
        _viewModel.addTask("Buy Groceries")
        _viewModel.addTask("Do Laundry")
        _viewModel.addTask("Go to the Gym")


        val taskId = _viewModel.tasks[1].id
        //Act - call the function being tested
        _viewModel.removeTask(taskId)

        //Assert - check the result
        assertEquals(2, _viewModel.getTaskCount())
        assertTrue(_viewModel.containsTask("Buy Groceries"))
        assertFalse(_viewModel.containsTask("Do Laundry"))
        assertTrue(_viewModel.containsTask("Go to the Gym"))
    }
}
