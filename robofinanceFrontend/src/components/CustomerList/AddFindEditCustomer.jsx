import s from "./AddFindEditCustomer.module.css"
import React, {Component} from "react";
import $ from "jquery";


export default class AddFindEditCustomer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            searchData: null,
            editCustomerData: null,
            name: "",
            lastName: "",
            middleName: "",
            sex: "",
            contry: "",
            region: "",
            city: "",
            street: "",
            house: "",
            flat: "",
            id: "",
            actual_address_id: "",

            currentSearch: "",
            addNewCustomerMode: true
        };
        this.onClickEdit = this.onClickEdit.bind(this);
    };


    putCustomer = (e) => {

        if(!this.checkInputData()){
            return;
        }

        let customer =
            {
                name: this.state.name,
                lastName: this.state.lastName,
                middleName: this.state.middleName,
                sex: this.state.sex,

                contry: this.state.contry,
                region: this.state.region,
                city: this.state.city,
                street: this.state.street,
                house: this.state.house,
                flat: this.state.flat
            }

        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080//putcustomer',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(customer),
            async: true,
            cache: false,
            success: function(data) {

            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });

        this.clearFieldsAndUpdate();
    }

    searchCustomer = () => {

        if(this.state.currentSearch.trim() === ""){
            alert(" write search request!! ")
            return;
        }

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080//getsearchresult',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data:  JSON.stringify({searchString: this.state.currentSearch}),
            async: false,
            cache: false,
            success: function(data) {
                this.setState({
                    searchData: data
                });

            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    }

    onClickEdit = (id) =>{

        var customerDataForEdit = this.state.searchData.map(function (item, index, array) {
            if (item.id == id) {
                 return item;
            }
        });

        this.setState({
            id:  customerDataForEdit[0].id,
            name: customerDataForEdit[0].first_name,
            lastName: customerDataForEdit[0].last_name,
            middleName: customerDataForEdit[0].middle_name,
            sex: customerDataForEdit[0].sex,
            actual_address_id: customerDataForEdit[0].actual_address_id,
            contry: customerDataForEdit[0].contry,
            region: customerDataForEdit[0].region,
            city: customerDataForEdit[0].city,
            street: customerDataForEdit[0].street,
            house: customerDataForEdit[0].house,
            flat: customerDataForEdit[0].flat,

        });

        this.state.addNewCustomerMode = false;
    }

    onClickUpdateCustomerAndAddress = () => {

        if(!this.checkInputData()){
            return;
        }

        let customer =
            {
                id: this.state.id,
                name: this.state.name,
                lastName: this.state.lastName,
                middleName: this.state.middleName,
                sex: this.state.sex,
                actual_address_id: this.state.actual_address_id,

                contry: this.state.contry,
                region: this.state.region,
                city: this.state.city,
                street: this.state.street,
                house: this.state.house,
                flat: this.state.flat
            }

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080//editcustomer',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(customer),
            async: true,
            cache: false,
            success: function(data) {

            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });

        this.state.addNewCustomerMode = true;

        this.clearFieldsAndUpdate();
    }

    checkInputData = () => {

        if( this.state.name.trim() === "" ||
            this.state.lastName.trim() === "" ||
            this.state.middleName.trim() === "" ||
            this.state.contry.trim() === "" ||
            this.state.region.trim() === "" ||
            this.state.city.trim() === "" ||
            this.state.street.trim() === "" ||
            this.state.house.trim() === "" ||
            this.state.flat.trim() === ""
        ){
            alert(" Fill in all the fields! ")
            return false;
        }else {
            return true;
        }

        if(this.state.sex !== "male" && this.state.sex !== "female"){
            alert("sex can be male or female");
            return false;
        }else {
            return true;
        }

    }

    clearFieldsAndUpdate = () =>{
        this.setState({
            name: "",
            lastName: "",
            middleName: "",
            sex: "",
            actual_address_id: "",
            contry: "",
            region: "",
            city: "",
            street: "",
            house: "",
            flat: "",

        });

        this.forceUpdate();
    }

    render() {
        const {searchData} = this.state;

            return (
                <div className = {s.grid}>

                    <div>
                        <h4>Customer Service</h4>
                        <input onChange={(e) => {this.state.currentSearch = e.target.value}}
                               defaultValue={this.state.currentSearch}
                               key={this.state.currentSearch}
                               placeholder="Поиск по имени или фамилии"
                               autoFocus
                               className={s.search}/>
                        <button onClick={(e) => {this.searchCustomer()}}>find and show</button>

                        {searchData ?
                            (searchData.map(item => (
                                    <div className={s.customer}>
                                        <p>Name: {item.first_name} {item.last_name} {item.middle_name}</p>
                                        <p> Actual address: {item.contry} {item.region} {item.city} {item.street} {item.house} {item.flat}</p>
                                        <p> sex: {item.sex}</p>
                                        <button onClick={() => {this.onClickEdit(item.id)}}>Edit</button>
                                    </div>
                                ))
                            )
                            : <p> Ничего не найдено</p>
                        }
                    </div>

                    <div className={s.editCustomers}>
                        <form className={s.formclass}>

                            <div className={s.inputBlock}>

                                {
                                    this.state.addNewCustomerMode ? <p>Add new customer</p>
                                        : <p>Edit customer </p>
                                }

                                <div>
                                    <span>name</span> <input onChange={(e)=>{this.state.name = e.target.value}}
                                                             key={this.state.name}
                                                             defaultValue={this.state.name}
                                                             type="text"/>
                                </div>

                                <div>
                                    <span>last name</span> <input onChange={(e)=>{this.state.lastName = e.target.value}}
                                                                  key={this.state.lastName}
                                                                  defaultValue={this.state.lastName}
                                                                  type="text"/>
                                </div>

                                <div>
                                    <span>middle name</span> <input onChange={(e)=>{this.state.middleName = e.target.value}}
                                                                    key={this.state.middleName}
                                                                    defaultValue={this.state.middleName}
                                                                    type="text"/>
                                </div>

                                <div>
                                    <span>sex</span> <input onChange={(e)=>{this.state.sex = e.target.value}}
                                                            key={this.state.sex}
                                                            defaultValue={this.state.sex}
                                                            type="text"/>
                                </div>

                                <p>Add address</p>

                                <div>
                                    <span>contry</span> <input onChange={(e)=>{this.state.contry = e.target.value}}
                                                               key={this.state.contry}
                                                               defaultValue={this.state.contry}
                                                               type="text"/>
                                </div>

                                <div>
                                    <span>region</span> <input onChange={(e)=>{this.state.region = e.target.value}}
                                                               key={this.state.region}
                                                               defaultValue={this.state.region}
                                                               type="text"/>
                                </div>

                                <div>
                                    <span>city</span> <input onChange={(e)=>{this.state.city = e.target.value}}
                                                             key={this.state.city}
                                                             defaultValue={this.state.city}
                                                             type="text"/>
                                </div>

                                <div>
                                    <span>street</span> <input onChange={(e)=>{this.state.street = e.target.value}}
                                                               key={this.state.street}
                                                               defaultValue={this.state.street}
                                                               type="text"/>
                                </div>

                                <div>
                                    <span>house</span> <input onChange={(e)=>{this.state.house = e.target.value}}
                                                               key={this.state.house}
                                                               defaultValue={this.state.house}
                                                               type="text"/>
                                </div>

                                <div>
                                    <span>flat</span> <input onChange={(e)=>{this.state.flat = e.target.value}}
                                                             key={this.state.flat}
                                                             defaultValue={this.state.flat}
                                                             type="text"/>
                                </div>

                            </div>

                        </form>

                        {
                            this.state.addNewCustomerMode ? <button onClick={(e) => {this.putCustomer(e)}}>Add new customer and him address</button>
                                : <button onClick={(e) => {this.onClickUpdateCustomerAndAddress(e)}}>Add edited customer and him address</button>
                        }

                    </div>
                </div>
            );

    }


}
