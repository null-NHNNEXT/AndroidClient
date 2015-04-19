package com.goznauk.projectnull.app.Model;


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
