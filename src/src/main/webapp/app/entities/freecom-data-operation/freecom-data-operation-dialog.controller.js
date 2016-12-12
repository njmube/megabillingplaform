(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_data_operationDialogController', Freecom_data_operationDialogController);

    Freecom_data_operationDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_data_operation', 'Freecom_public_notaries'];

    function Freecom_data_operationDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_data_operation, Freecom_public_notaries) {
        var vm = this;
        vm.freecom_data_operation = entity;
        vm.freecom_public_notariess = Freecom_public_notaries.query({filter: 'freecom_data_operation-is-null'});
        $q.all([vm.freecom_data_operation.$promise, vm.freecom_public_notariess.$promise]).then(function() {
            if (!vm.freecom_data_operation.freecom_public_notaries || !vm.freecom_data_operation.freecom_public_notaries.id) {
                return $q.reject();
            }
            return Freecom_public_notaries.get({id : vm.freecom_data_operation.freecom_public_notaries.id}).$promise;
        }).then(function(freecom_public_notaries) {
            vm.freecom_public_notariess.push(freecom_public_notaries);
        });
        vm.load = function(id) {
            Freecom_data_operation.get({id : id}, function(result) {
                vm.freecom_data_operation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_data_operationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_data_operation.id !== null) {
                Freecom_data_operation.update(vm.freecom_data_operation, onSaveSuccess, onSaveError);
            } else {
                Freecom_data_operation.save(vm.freecom_data_operation, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.dateinstnotarial = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
