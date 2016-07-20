(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('AuditsController', AuditsController);

    AuditsController.$inject = ['$filter', 'AuditsService', 'ParseLinks', 'Audit_event_type'];

    function AuditsController ($filter, AuditsService, ParseLinks, Audit_event_type) {
        var vm = this;

        vm.audits = null;
        vm.fromDate = null;
        vm.links = null;
        vm.loadPage = loadPage;
        vm.search = search;
        vm.page = 1;
        vm.toDate = null;
        vm.totalItems = null;
        vm.principal = null;
        vm.ip = null;
        vm.auditEventType = null;
        vm.auditEventTypeS = Audit_event_type.query({pg:1});
        vm.search();

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

            AuditsService.query({
                page: vm.page -1,
                size: 20,
                fromDate: fromDate,
                toDate: toDate,
                principal: principal,
                auditEventType: auditEventType,
                ip:ip},
                function(result, headers){
                vm.audits = result;
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
            });
        }


        function loadPage (page) {
            vm.page = page;
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
