(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_accreditation_iepsDialogController', Com_accreditation_iepsDialogController);

    Com_accreditation_iepsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_accreditation_ieps', 'C_tar', 'Cfdi'];

    function Com_accreditation_iepsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Com_accreditation_ieps, C_tar, Cfdi) {
        var vm = this;
        vm.com_accreditation_ieps = entity;
        vm.c_tars = C_tar.query();
        vm.cfdis = Cfdi.query({filter: 'com_accreditation_ieps-is-null'});
        $q.all([vm.com_accreditation_ieps.$promise, vm.cfdis.$promise]).then(function() {
            if (!vm.com_accreditation_ieps.cfdi || !vm.com_accreditation_ieps.cfdi.id) {
                return $q.reject();
            }
            return Cfdi.get({id : vm.com_accreditation_ieps.cfdi.id}).$promise;
        }).then(function(cfdi) {
            vm.cfdis.push(cfdi);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_accreditation_iepsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_accreditation_ieps.id !== null) {
                Com_accreditation_ieps.update(vm.com_accreditation_ieps, onSaveSuccess, onSaveError);
            } else {
                Com_accreditation_ieps.save(vm.com_accreditation_ieps, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
