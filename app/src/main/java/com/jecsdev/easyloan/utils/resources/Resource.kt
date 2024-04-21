package com.jecsdev.easyloan.utils.resources

/**
 * A sealed class representing different resource states that can be consumed by an application.
 * Resources can represent data from various sources such as network, database, etc.
 *
 * @param T The type of data contained in the resource.
 * @property data The data associated with the resource.
 * @property message An optional descriptive message associated with the resource state.
 */
sealed class Resource<T>(val data: T? = null, private val message: String? = null) {

    /**
     * Represents a successful state with the associated data.
     *
     * @param data The data obtained successfully.
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Represents an error state with an associated error message.
     *
     * @param message The descriptive error message.
     * @param data Optional additional data associated with the error state.
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    /**
     * Represents a loading state, indicating that data is being loaded.
     *
     * @param data Optional data associated with the loading state.
     */
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
