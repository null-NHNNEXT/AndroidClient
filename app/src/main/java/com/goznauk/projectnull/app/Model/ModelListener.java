package com.goznauk.projectnull.app.Model;


/**
 * Created by goznauk on 2015. 4. 3..
 */
public interface ModelListener<MODEL extends BaseModel> {
    void onModelUpdated(MODEL model);
}
