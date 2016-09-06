(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Request_taxpayer_accountController', Request_taxpayer_accountController);

    Request_taxpayer_accountController.$inject = ['$scope', '$state', '$filter', 'Request_state', 'Request_taxpayer_account', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function Request_taxpayer_accountController ($scope, $state, $filter, Request_state, Request_taxpayer_account, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.filterrfc = null;
        vm.datefrom = null;
        vm.dateto = null;
        vm.filtername = null;
        vm.filterfirtsurname = null;
        vm.filtersecondsurname = null;
        vm.filterbussines_name = null;
        vm.filteraccount_email = null;
        vm.request_state = null;
        vm.filteruser = null;
        vm.search = search;
        vm.request_stateS = Request_state.query({filtername: " "});

        loadAll();

        function loadAll () {
            Request_taxpayer_account.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.request_taxpayer_accounts = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
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
            var filtername = " ";
            if(vm.filtername != null && vm.filtername != ""){
                filtername = vm.filtername;
            }
            var filterfirtsurname = " ";
            if(vm.filterfirtsurname != null && vm.filterfirtsurname != ""){
                filterfirtsurname = vm.filterfirtsurname;
            }
            var filtersecondsurname = " ";
            if(vm.filtersecondsurname != null && vm.filtersecondsurname != ""){
                filtersecondsurname = vm.filtersecondsurname;
            }
            var filterbussines_name = " ";
            if(vm.filterbussines_name != null && vm.filterbussines_name != ""){
                filterbussines_name = vm.filterbussines_name;
            }
            var filteraccount_email = " ";
            if(vm.filteraccount_email != null && vm.filteraccount_email != ""){
                filteraccount_email = vm.filteraccount_email;
            }
            var request_state = null;
            if(vm.request_state != null){
                request_state = vm.request_state;
            }
            var filteruser = null;
            if(vm.filteruser != null){
                filteruser = vm.filteruser;
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
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
