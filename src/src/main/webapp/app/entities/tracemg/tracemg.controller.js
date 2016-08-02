(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('TracemgController', TracemgController);

    TracemgController.$inject = ['$scope','$filter', '$state', 'Tracemg', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants', 'Audit_event_type'];

    function TracemgController ($scope, $filter, $state, Tracemg, ParseLinks, AlertService, pagingParams, paginationConstants, Audit_event_type) {
        var vm = this;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.auditEventTypeS = Audit_event_type.query({pg:1});
        vm.principal = null;
        vm.ip = null;
        vm.auditEventType = null;
        vm.fromDate = null;
        vm.toDate = null;
        vm.search = search;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.loadAll();

        function loadAll () {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')("0000-01-01", dateFormat);
            var toDate = $filter('date')("0000-01-01", dateFormat);
            var principal = " ";
            var auditEventType = " ";
            var ip = " ";
            Tracemg.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                fromDate: fromDate,
                toDate: toDate,
                principal: principal,
                auditEventType: auditEventType,
                ip:ip
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
                vm.tracemgs = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function search(){
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')("0000-01-01", dateFormat);
            var toDate = $filter('date')("0000-01-01", dateFormat);
            if(vm.fromDate != null){
                fromDate = $filter('date')(vm.fromDate, dateFormat);
            }
            if(vm.toDate != null){
                toDate = $filter('date')(vm.toDate, dateFormat);
            }
            var principal = " ";
            if(vm.principal != null && vm.principal != "") {
                principal = vm.principal;
            }
            var auditEventType = " ";
            if(vm.auditEventType != null) {
                auditEventType = vm.auditEventType.name;
            }
            var ip = " ";
            if(vm.ip != null && vm.ip != ""){
                ip = vm.ip;
            }
            Tracemg.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                fromDate: fromDate,
                toDate: toDate,
                principal: principal,
                auditEventType: auditEventType,
                ip:ip
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
                vm.tracemgs = data;
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
