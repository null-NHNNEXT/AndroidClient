package com.goznauk.projectnull.app.Model;

/**
 * Created by goznauk on 2015. 3. 27..
 */
public abstract class BaseModel {
    private ModelListener modelListener;

    public void setModelListener(ModelListener modelListener) {
        this.modelListener = modelListener;
    }

    @SuppressWarnings("unchecked")
    public void update() {
        modelListener.onModelUpdated(this);
    }
}
