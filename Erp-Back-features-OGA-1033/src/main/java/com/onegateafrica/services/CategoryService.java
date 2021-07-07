package com.onegateafrica.services;

import com.onegateafrica.Entity.AppelDoffreEntity;
import com.onegateafrica.Entity.Category;

import java.util.Optional;

public interface CategoryService {
    public Optional<Category> getCategorieByIdForResponse(long id);
}
