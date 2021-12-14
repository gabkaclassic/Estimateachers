<#import "parts/main.ftl" as main>
<#import "parts/users_logic.ftl" as ul>
<#import "parts/security.ftl" as security>
<#import "parts/date.ftl" as date>

<@main.page>

    <div class="mt-3 row">
        <div class="column mr-5">
        <div class="accordion" id="forms_add_new_card">
            <div class="accordion-item">
                <h2 class="accordion-header" id="headingOne">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        Add university
                    </button>
                </h2>
                <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#forms_add_new_card">
                    <div class="accordion-body">
                        <form class="d-block w-100" method = "post" action = "/cards/add/university">
                            <div class="form-group row">
                                <input name = "title" type = "text" placeholder = "University title" /> <br>
                            </div>
                            <div class="form-group row">
                                <div class="form-group col">
                                    <input name="bachelor" type="checkbox" aria-label="Bachelor">Bachelor
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="form-group col">
                                    <input name="magistracy" type="checkbox" aria-label="Magistracy">Magistracy
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="form-group col">
                                    <input name="specialty" type="checkbox" aria-label="Specialty">Specialty
                                </div>
                            </div>
                            <@date.date />
                            <@security.token />
                            <button type = "submit" class="btn btn-primary">Add university</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header" id="headingTwo">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                        Add faculty
                    </button>
                </h2>
                <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#forms_add_new_card">
                    <div class="accordion-body">
                        <form class="d-block w-100" method = "post" action = "/cards/add/faculty">
                            <input name = "title" type = "text" placeholder = "Title faculty" />
                            <div class="form-group row">
                                <span> University: <@ul.select enum = universities name = "university" /> </span>
                            </div>
                            <div class="form-group row">
                                <span> Teachers: <@ul.m_select enum = teachers name = "teachers" /> </span>
                            </div>
                            <@date.date />
                            <@security.token />
                            <button type = "submit" class="btn btn-primary">Add faculty</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header" id="headingThree">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                        Add dormitory
                    </button>
                </h2>
                <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#forms_add_new_card">
                    <div class="accordion-body">
                        <form class="d-block w-100" method = "post" action = "/cards/add/dormitory">
                            <input name = "title" type = "text" placeholder = "Title dormitory" /> <br>
                            <div class="form-group row">
                                <span> University: <@ul.select enum = universities name = "university" /> </span>
                            </div>
                            <@date.date />
                            <@security.token />
                            <button type = "submit" class="btn btn-primary">Add dormitory</button>
                        </form>
                    </div>
                </div>
            </div>
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingFor">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFor" aria-expanded="false" aria-controls="collapseFor">
                            Add teacher
                        </button>
                    </h2>
                    <div id="collapseFor" class="accordion-collapse collapse" aria-labelledby="headingFor" data-bs-parent="#forms_add_new_card">
                        <div class="accordion-body">
                            <form class="d-block w-100" method = "post" action = "/cards/add/teacher">
                                <div class="form-group row">
                                    <label class="col-sm-2 col-form-label">First name: </label>
                                    <div class="col-sm-4">
                                        <input type = "text" id = "firstName" name = "firstName" placeholder = "Teacher first name" class="form-control"/>
                                        <div id="firstNameHelpBlock" class="form-text">
                                            The first name must consist of 2-32 letters
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-sm-2 col-form-label">Last name: </label>
                                    <div class="col-sm-4">
                                        <input type = "text" id = "lastName" name = "lastName" placeholder = "Teacher last name" class="form-control"/>
                                        <div id="lastNameHelpBlock" class="form-text">
                                            The last name must consist of 2-32 letters
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-sm-2 col-form-label">Patronymic: </label>
                                    <div class="col-sm-4">
                                        <input type = "text" id = "patronymic" name = "patronymic" placeholder = "Teacher patronymic" class="form-control"/>
                                        <div id="patronymicHelpBlock" class="form-text">
                                            The patronymic must consist of 2-32 letters
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <span> Universities: <@ul.m_select enum = universities name = "universities" /> </span>
                                </div>
                                <div class="form-group row">
                                    <span> Faculties: <@ul.m_select enum = faculties name = "faculties" /> </span>
                                </div>
                                <@date.date />
                                <@security.token />
                                <button type = "submit" class="btn btn-primary">Add teacher</button>
                            </form>
                        </div>
                </div>
            </div>
        </div>
        </div>
        <div class="column ml-3">
            <#if remarks??>
                <@ul.foreach collection = remarks![] status="danger" />
            </#if>
        </div>
     </div>

</@main.page>