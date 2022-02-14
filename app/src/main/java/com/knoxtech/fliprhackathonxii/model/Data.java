package com.knoxtech.fliprhackathonxii.model;

public class Data {
    String name,mobile,nature_of_material,weight,quantity,city,state,age,truck_no,capacity,transporter_name,experience,route1,route2,route3,status,from_lat,from_long,driver_name,from,to,driver_mob;

    String dealer_name,dealer_mob,dealer_weight,dealer_city,dealer_state,docId;
    public Data() {
    }


    public Data(String name, String mobile, String nature_of_material, String weight, String quantity, String city, String state, String age, String truck_no, String capacity, String transporter_name, String experience, String route1, String route2, String route3, String status, String from_lat, String from_long, String driver_name, String from, String to, String driver_mob, String dealer_name, String dealer_mob, String dealer_weight, String dealer_city, String dealer_state,String docId) {
        this.name = name;
        this.mobile = mobile;
        this.nature_of_material = nature_of_material;
        this.weight = weight;
        this.quantity = quantity;
        this.city = city;
        this.state = state;
        this.age = age;
        this.truck_no = truck_no;
        this.capacity = capacity;
        this.transporter_name = transporter_name;
        this.experience = experience;
        this.route1 = route1;
        this.route2 = route2;
        this.route3 = route3;
        this.status = status;
        this.from_lat = from_lat;
        this.from_long = from_long;
        this.driver_name = driver_name;
        this.from = from;
        this.to = to;
        this.driver_mob = driver_mob;
        this.dealer_name = dealer_name;
        this.dealer_mob = dealer_mob;
        this.dealer_weight = dealer_weight;
        this.dealer_city = dealer_city;
        this.docId=docId;
        this.dealer_state = dealer_state;
    }

    public String getDriver_mob() {
        return driver_mob;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getDealer_mob() {
        return dealer_mob;
    }

    public void setDealer_mob(String dealer_mob) {
        this.dealer_mob = dealer_mob;
    }

    public String getDealer_weight() {
        return dealer_weight;
    }

    public void setDealer_weight(String dealer_weight) {
        this.dealer_weight = dealer_weight;
    }

    public String getDealer_city() {
        return dealer_city;
    }

    public void setDealer_city(String dealer_city) {
        this.dealer_city = dealer_city;
    }

    public String getDealer_state() {
        return dealer_state;
    }

    public void setDealer_state(String dealer_state) {
        this.dealer_state = dealer_state;
    }

    public void setDriver_mob(String driver_mob) {
        this.driver_mob = driver_mob;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNature_of_material() {
        return nature_of_material;
    }

    public void setNature_of_material(String nature_of_material) {
        this.nature_of_material = nature_of_material;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTruck_no() {
        return truck_no;
    }

    public void setTruck_no(String truck_no) {
        this.truck_no = truck_no;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getTransporter_name() {
        return transporter_name;
    }

    public void setTransporter_name(String transporter_name) {
        this.transporter_name = transporter_name;
    }

    public String getFrom_lat() {
        return from_lat;
    }

    public void setFrom_lat(String from_lat) {
        this.from_lat = from_lat;
    }

    public String getFrom_long() {
        return from_long;
    }

    public void setFrom_long(String from_long) {
        this.from_long = from_long;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getRoute1() {
        return route1;
    }

    public void setRoute1(String route1) {
        this.route1 = route1;
    }

    public String getRoute2() {
        return route2;
    }

    public void setRoute2(String route2) {
        this.route2 = route2;
    }

    public String getRoute3() {
        return route3;
    }

    public void setRoute3(String route3) {
        this.route3 = route3;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
