(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Customs_info_partDialogController', Customs_info_partDialogController);

    Customs_info_partDialogController.$inject = ['$timeout', '$uibModalInstance', 'entity'];

    function Customs_info_partDialogController ($timeout, $uibModalInstance, entity) {
        var vm = this;
        vm.customs_info_part = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.customs_info_part);
            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
