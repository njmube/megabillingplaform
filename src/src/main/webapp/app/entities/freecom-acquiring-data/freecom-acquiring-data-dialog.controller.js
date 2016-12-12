(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_acquiring_dataDialogController', Freecom_acquiring_dataDialogController);

    Freecom_acquiring_dataDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_acquiring_data', 'Freecom_public_notaries'];

    function Freecom_acquiring_dataDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_acquiring_data, Freecom_public_notaries) {
        var vm = this;
        vm.freecom_acquiring_data = entity;
        vm.freecom_public_notariess = Freecom_public_notaries.query({filter: 'freecom_acquiring_data-is-null'});
        $q.all([vm.freecom_acquiring_data.$promise, vm.freecom_public_notariess.$promise]).then(function() {
            if (!vm.freecom_acquiring_data.freecom_public_notaries || !vm.freecom_acquiring_data.freecom_public_notaries.id) {
                return $q.reject();
            }
            return Freecom_public_notaries.get({id : vm.freecom_acquiring_data.freecom_public_notaries.id}).$promise;
        }).then(function(freecom_public_notaries) {
            vm.freecom_public_notariess.push(freecom_public_notaries);
        });
        vm.load = function(id) {
            Freecom_acquiring_data.get({id : id}, function(result) {
                vm.freecom_acquiring_data = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_acquiring_dataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_acquiring_data.id !== null) {
                Freecom_acquiring_data.update(vm.freecom_acquiring_data, onSaveSuccess, onSaveError);
            } else {
                Freecom_acquiring_data.save(vm.freecom_acquiring_data, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
