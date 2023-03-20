<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 3/18/2023
  Time: 5:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Person</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <h1>Search Person</h1>
        <button class="btn btn-light mb-2" data-toggle="modal" data-target="#createModal">Create person</button>
        <div class="row">
            <form class="form-inline" action="person" method="post">
                <div class="form-group mx-sm-3 mb-2">
                    <label for="searchName" class="sr-only">Name</label>
                    <input type="text" class="form-control" id="searchName" name="name" placeholder="Name">
                </div>
                <input class="btn btn-primary mb-2" type="submit" value="Search" name="action">
            </form>
        </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Phone 1</th>
                <th scope="col">Phone 2</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${persons}" var="person">
                <tr scope="row">
                    <td>${person.name}</td>
                    <td>${person.phones.size() > 0 ? person.phones.get(0).number : ''}</td>
                    <td>${person.phones.size() > 1 ? person.phones.get(1).number : ''}</td>
                    <td>
                        <div class="d-inline-flex">
                            <button class="btn btn-primary mr-2" onclick="fetchPerson(${person.id})" data-toggle="modal" data-target="#updateModal">Edit</button>
                            <form action="person" method="post" onsubmit="return confirm('Are you sure you want to delete this record?');" style="margin-block-end: 0">
                                <input type="hidden" name="id" value="${person.id}"/>
                                <button class="btn btn-danger" type="submit" name="action" value="Delete">Delete</button>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- Create Modal -->
        <div class="modal fade" id="createModal" tabindex="-1" role="dialog" aria-labelledby="createModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form action="person" method="post">
                        <div class="modal-header">
                            <h5 class="modal-title" id="createModalLabel">Create Person</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                                <div class="row form-group mb-3">
                                    <label class="col-sm-4 col-form-label float-right">Name</label>
                                    <div class="col-sm-8">
                                        <input class="form-control" name="name" type="text">
                                    </div>
                                </div>
                                <div class="row form-group mb-3">
                                    <label class="col-sm-4 col-form-label float-right">Number 1</label>
                                    <div class="col-sm-8">
                                        <input class="form-control" name="number1" type="text">
                                    </div>
                                </div>
                                <div class="row form-group mb-3">
                                    <label class="col-sm-4 col-form-label float-right">Number 2</label>
                                    <div class="col-sm-8">
                                        <input class="form-control" name="number2" type="text">
                                    </div>
                                </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary" name="action" value="Add">Create</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Update Modal -->
        <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form action="person" method="post">
                        <div class="modal-header">
                            <h5 class="modal-title" id="updateModalLabel">Update Person</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input class="form-control" id="id" name="id"  type="hidden" />
                            <div class="row form-group mb-3">
                                <label class="col-sm-4 col-form-label float-right" for="name">Name</label>
                                <div class="col-sm-8">
                                    <input class="form-control" id="name" name="name"  type="text" />
                                </div>
                            </div>
                            <div class="row form-group mb-3">
                                <label class="col-sm-4 col-form-label float-right" for="number1">Number 1</label>
                                <div class="col-sm-8">
                                    <input class="form-control" id="number1" name="number1" type="text" />
                                </div>
                            </div>
                            <div class="row form-group mb-3">
                                <label class="col-sm-4 col-form-label float-right" for="number2">Number 2</label>
                                <div class="col-sm-8">
                                    <input class="form-control" id="number2" name="number2" type="text" />
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary" name="action" value="Update">Save changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script>
        function fetchPerson(id) {
            let xhr = new XMLHttpRequest();
            xhr.responseType = 'json';
            xhr.open("GET", "/person?action=update&id="+id, true);
            xhr.onload = function() {
                if (xhr.status === 200) {
                    // Success
                    let person = xhr.response;
                    console.log(person);
                    document.getElementById('id').value = person.id;
                    document.getElementById('name').value = person.name;
                    document.getElementById('number1').value = person.phones ? person.phones[0].number : '';
                    document.getElementById('number2').value = person.phones.length > 1 ? person.phones[1].number : '';
                } else {
                    // Error
                    console.error(xhr.statusText);
                }
            };
            xhr.onerror = function() {
                console.error(xhr.statusText);
            };
            xhr.send();
        }
    </script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
