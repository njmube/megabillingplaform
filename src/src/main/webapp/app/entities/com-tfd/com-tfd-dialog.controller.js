(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_tfdDialogController', Com_tfdDialogController);

    Com_tfdDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_tfd'];

    function Com_tfdDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_tfd) {
        var vm = this;

        vm.com_tfd = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.com_tfd.id !== null) {
                Com_tfd.update(vm.com_tfd, onSaveSuccess, onSaveError);
            } else {
                Com_tfd.save(vm.com_tfd, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:com_tfdUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.stamp_date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
