package org.gab.estimateachers.app.services;

import org.gab.estimateachers.entities.client.Card;

public interface CardService<T extends Card> extends Service<T> {
    
    boolean existsByTitle(String title);
    
}
