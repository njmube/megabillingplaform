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
        vm.accept = accept;
        vm.reject = reject;
        vm.filterrfc = null;
        vm.datefrom = null;
        vm.dateto = null;
        vm.request_state = null;
        vm.search = search;
        vm.request_stateS = Request_state.query({filtername: " "});

        loadAll();

        function loadAll () {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')("0000-01-01", dateFormat);
            var toDate = $filter('date')("0000-01-01", dateFormat);

            Request_taxpayer_account.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                //sort: sort(),
                datefrom: fromDate,
                dateto: toDate,
                request_state: -1
            }, onSuccess, onError);
            /*function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }*/
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
            var request_state = -1;
            if(vm.request_state != null){
                request_state = vm.request_state.id;
            }

            Request_taxpayer_account.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                //sort: sort(),
                datefrom: fromDate,
                dateto: toDate,
                request_state: request_state
            }, onSuccess, onError);
            /*function sort() {
             var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
             if (vm.predicate !== 'id') {
             result.push('id');
             }
             return result;
             }*/
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

        function accept(request_taxpayer_account){
            request_taxpayer_account.request_state = vm.request_stateS[1];
            request_taxpayer_account = Request_taxpayer_account.update(request_taxpayer_account);
        }

        function reject(request_taxpayer_account){
            request_taxpayer_account.request_state = vm.request_stateS[2];
            request_taxpayer_account = Request_taxpayer_account.update(request_taxpayer_account);
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
