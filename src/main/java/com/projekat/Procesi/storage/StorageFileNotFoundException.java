package com.projekat.Procesi.storage;

public class StorageFileNotFoundException extends StorageException {

	private static final long serialVersionUID = -1247566654624558725L;

	public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}