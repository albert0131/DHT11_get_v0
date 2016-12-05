package com.example.user.dht11_get_v0;

/**
 * Created by user on 2016/12/2.
 */

public class Feeds {
    private String created_at;  // data create time
    private String entry_id;    // data count
    private String field1;      // temperature
    private String field2;      // Humidity
    private String field3;      // PM2.5

    public Feeds(){}

    public void setCreated_at(String created_at){
        this.created_at = created_at;
    }
    public String getCreated_at(){
        return created_at;
    }

    public void setEntry_id(String entry_id){
        this.entry_id = entry_id;
    }
    public String getEntry_id(){
        return entry_id;
    }

    public void setField1(String field1){
        this.field1 = field1;
    }
    public String getField1(){
        return field1;
    }

    public void setField2(String field2){
        this.field2 = field2;
    }
    public String getField2(){
        return field2;
    }

    public void setField3(String field3){
        this.field3 = field3;
    }
    public String getField3(){
        return field3;
    }
}
