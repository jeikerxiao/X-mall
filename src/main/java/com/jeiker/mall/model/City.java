package com.jeiker.mall.model;

public class City {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column city.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column city.country
     *
     * @mbggenerated
     */
    private String country;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column city.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column city.state
     *
     * @mbggenerated
     */
    private String state;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column city.id
     *
     * @return the value of city.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column city.id
     *
     * @param id the value for city.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column city.country
     *
     * @return the value of city.country
     *
     * @mbggenerated
     */
    public String getCountry() {
        return country;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column city.country
     *
     * @param country the value for city.country
     *
     * @mbggenerated
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column city.name
     *
     * @return the value of city.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column city.name
     *
     * @param name the value for city.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column city.state
     *
     * @return the value of city.state
     *
     * @mbggenerated
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column city.state
     *
     * @param state the value for city.state
     *
     * @mbggenerated
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}