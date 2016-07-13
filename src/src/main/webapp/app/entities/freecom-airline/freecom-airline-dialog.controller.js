(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_airlineDialogController', Freecom_airlineDialogController);

    Freecom_airlineDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_airline', 'Free_cfdi'];

    function Freecom_airlineDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_airline, Free_cfdi) {
        var vm = this;
        vm.freecom_airline = entity;
        vm.free_cfdis = Free_cfdi.query({filter: 'freecom_airline-is-null'});
        $q.all([vm.freecom_airline.$promise, vm.free_cfdis.$promise]).then(function() {
            if (!vm.freecom_airline.free_cfdi || !vm.freecom_airline.free_cfdi.id) {
                return $q.reject();
            }
            return Free_cfdi.get({id : vm.freecom_airline.free_cfdi.id}).$promise;
        }).then(function(free_cfdi) {
            vm.free_cfdis.push(free_cfdi);
        });
        vm.load = function(id) {
            Freecom_airline.get({id : id}, function(result) {
                vm.freecom_airline = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_airlineUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_airline.id !== null) {
                Freecom_airline.update(vm.freecom_airline, onSaveSuccess, onSaveError);
            } else {
                Freecom_airline.save(vm.freecom_airline, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
