(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_taxregistrationDialogController', Freecom_taxregistrationDialogController);

    Freecom_taxregistrationDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_taxregistration', 'Free_cfdi'];

    function Freecom_taxregistrationDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_taxregistration, Free_cfdi) {
        var vm = this;
        vm.freecom_taxregistration = entity;
        vm.free_cfdis = Free_cfdi.query({filter: 'freecom_taxregistration-is-null'});
        $q.all([vm.freecom_taxregistration.$promise, vm.free_cfdis.$promise]).then(function() {
            if (!vm.freecom_taxregistration.free_cfdi || !vm.freecom_taxregistration.free_cfdi.id) {
                return $q.reject();
            }
            return Free_cfdi.get({id : vm.freecom_taxregistration.free_cfdi.id}).$promise;
        }).then(function(free_cfdi) {
            vm.free_cfdis.push(free_cfdi);
        });
        vm.load = function(id) {
            Freecom_taxregistration.get({id : id}, function(result) {
                vm.freecom_taxregistration = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_taxregistrationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_taxregistration.id !== null) {
                Freecom_taxregistration.update(vm.freecom_taxregistration, onSaveSuccess, onSaveError);
            } else {
                Freecom_taxregistration.save(vm.freecom_taxregistration, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
