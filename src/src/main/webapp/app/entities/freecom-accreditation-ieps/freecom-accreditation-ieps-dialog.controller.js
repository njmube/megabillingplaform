(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_accreditation_iepsDialogController', Freecom_accreditation_iepsDialogController);

    Freecom_accreditation_iepsDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_accreditation_ieps', 'C_tar', 'Free_cfdi'];

    function Freecom_accreditation_iepsDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_accreditation_ieps, C_tar, Free_cfdi) {
        var vm = this;
        vm.freecom_accreditation_ieps = entity;
        vm.c_tars = C_tar.query();
        vm.free_cfdis = Free_cfdi.query({filter: 'freecom_accreditation_ieps-is-null'});
        $q.all([vm.freecom_accreditation_ieps.$promise, vm.free_cfdis.$promise]).then(function() {
            if (!vm.freecom_accreditation_ieps.free_cfdi || !vm.freecom_accreditation_ieps.free_cfdi.id) {
                return $q.reject();
            }
            return Free_cfdi.get({id : vm.freecom_accreditation_ieps.free_cfdi.id}).$promise;
        }).then(function(free_cfdi) {
            vm.free_cfdis.push(free_cfdi);
        });
        vm.load = function(id) {
            Freecom_accreditation_ieps.get({id : id}, function(result) {
                vm.freecom_accreditation_ieps = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_accreditation_iepsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_accreditation_ieps.id !== null) {
                Freecom_accreditation_ieps.update(vm.freecom_accreditation_ieps, onSaveSuccess, onSaveError);
            } else {
                Freecom_accreditation_ieps.save(vm.freecom_accreditation_ieps, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
