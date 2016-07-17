(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('UserManagementController', UserManagementController);

    UserManagementController.$inject = ['Principal', '$filter','User', 'ParseLinks', 'paginationConstants', 'JhiLanguageService'];

    function UserManagementController(Principal, $filter,User, ParseLinks, paginationConstants, JhiLanguageService) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_ADMIN_CF', 'ROLE_AFILITATED', 'ROLE_OPERADOR', 'ROLE_USER_CF'];
        vm.clear = clear;
        vm.currentAccount = null;
        vm.languages = null;
        vm.links = null;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.page = 1;
        vm.setActive = setActive;
        vm.totalItems = null;
        vm.users = [];
        vm.filterrfc = null;
        vm.datefrom = null;
        vm.dateto = null;
        vm.stateuser = null;
        vm.role = null;
        vm.search = search;

        vm.loadAll();

        JhiLanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });

        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });


        function loadAll () {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')("0000-01-01", dateFormat);
            var toDate = $filter('date')("0000-01-01", dateFormat);
            User.query({
                page: vm.page - 1,
                size: paginationConstants.itemsPerPage,
                filterrfc: " ",
                datefrom: fromDate,
                dateto: toDate,
                stateuser: -1,
                role: " "},
                function (result, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.users = result;
            });
        }

        function loadPage (page) {
            vm.page = page;
            vm.loadAll();
        }

        function setActive (user, isActivated) {
            user.activated = isActivated;
            User.update(user, function () {
                vm.loadAll();
                vm.clear();
            });
        }

        function clear () {
            vm.user = {
                id: null, login: null, rfc: null, name: null, firstsurname: null, secondsurname: null,
                email: null, phone: null, gender: null,
                activated: null, langKey: null, createdBy: null, createdDate: null,
                lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
                resetKey: null, authorities: null
            };
            vm.editForm.$setPristine();
            vm.editForm.$setUntouched();
        }

        function search() {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')("0000-01-01", dateFormat);
            var toDate = $filter('date')("0000-01-01", dateFormat);
            if(vm.datefrom != null){
                fromDate = $filter('date')(vm.datefrom, dateFormat);
            }
            if(vm.dateto != null){
                toDate = $filter('date')(vm.dateto, dateFormat);
            }
            var filterrfc = " ";
            if(vm.filterrfc != null && vm.filterrfc != ""){
                filterrfc = vm.filterrfc;
            }
            var stateuser = -1;
            if(vm.stateuser != null && vm.stateuser !=""){
                stateuser = vm.stateuser;
            }
            var role = " ";
            if(vm.role != null && vm.role!=""){
                role = vm.role;
            }
            User.query({
                    page: vm.page - 1,
                    size: paginationConstants.itemsPerPage,
                    filterrfc: filterrfc,
                    datefrom: fromDate,
                    dateto: toDate,
                    stateuser: stateuser,
                    role: role},
                function (result, headers) {
                    vm.links = ParseLinks.parse(headers('link'));
                    vm.totalItems = headers('X-Total-Count');
                    vm.users = result;
                });
        }

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };

        vm.datePickerOpenStatus1 = {};
        vm.datePickerOpenStatus1.date = false;

        vm.openCalendar1 = function(date) {
            vm.datePickerOpenStatus1[date] = true;
        };
    }
})();
