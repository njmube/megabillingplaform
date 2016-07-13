(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_doneesDialogController', Freecom_doneesDialogController);

    Freecom_doneesDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_donees', 'Free_cfdi'];

    function Freecom_doneesDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_donees, Free_cfdi) {
        var vm = this;
        vm.freecom_donees = entity;
        vm.free_cfdis = Free_cfdi.query({filter: 'freecom_donees-is-null'});
        $q.all([vm.freecom_donees.$promise, vm.free_cfdis.$promise]).then(function() {
            if (!vm.freecom_donees.free_cfdi || !vm.freecom_donees.free_cfdi.id) {
                return $q.reject();
            }
            return Free_cfdi.get({id : vm.freecom_donees.free_cfdi.id}).$promise;
        }).then(function(free_cfdi) {
            vm.free_cfdis.push(free_cfdi);
        });
        vm.load = function(id) {
            Freecom_donees.get({id : id}, function(result) {
                vm.freecom_donees = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_doneesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_donees.id !== null) {
                Freecom_donees.update(vm.freecom_donees, onSaveSuccess, onSaveError);
            } else {
                Freecom_donees.save(vm.freecom_donees, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date_authorization = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
