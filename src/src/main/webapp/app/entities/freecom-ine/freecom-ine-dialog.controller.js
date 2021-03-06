(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ineDialogController', Freecom_ineDialogController);

    Freecom_ineDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_ine', 'Free_cfdi', 'C_committee_type', 'C_process_type'];

    function Freecom_ineDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_ine, Free_cfdi, C_committee_type, C_process_type) {
        var vm = this;
        vm.freecom_ine = entity;
        vm.free_cfdis = Free_cfdi.query({filter: 'freecom_ine-is-null'});
        $q.all([vm.freecom_ine.$promise, vm.free_cfdis.$promise]).then(function() {
            if (!vm.freecom_ine.free_cfdi || !vm.freecom_ine.free_cfdi.id) {
                return $q.reject();
            }
            return Free_cfdi.get({id : vm.freecom_ine.free_cfdi.id}).$promise;
        }).then(function(free_cfdi) {
            vm.free_cfdis.push(free_cfdi);
        });
        vm.c_committee_types = C_committee_type.query();
        vm.c_process_types = C_process_type.query();
        vm.load = function(id) {
            Freecom_ine.get({id : id}, function(result) {
                vm.freecom_ine = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_ineUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_ine.id !== null) {
                Freecom_ine.update(vm.freecom_ine, onSaveSuccess, onSaveError);
            } else {
                Freecom_ine.save(vm.freecom_ine, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
