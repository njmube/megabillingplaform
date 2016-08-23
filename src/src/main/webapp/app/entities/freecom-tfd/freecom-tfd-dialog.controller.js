(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_tfdDialogController', Freecom_tfdDialogController);

    Freecom_tfdDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_tfd'];

    function Freecom_tfdDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_tfd) {
        var vm = this;
        vm.freecom_tfd = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_tfdUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_tfd.id !== null) {
                Freecom_tfd.update(vm.freecom_tfd, onSaveSuccess, onSaveError);
            } else {
                Freecom_tfd.save(vm.freecom_tfd, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.stamp_date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
