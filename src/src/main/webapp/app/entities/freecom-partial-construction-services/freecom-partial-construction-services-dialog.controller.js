(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_partial_construction_servicesDialogController', Freecom_partial_construction_servicesDialogController);

    Freecom_partial_construction_servicesDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_partial_construction_services', 'Free_cfdi', 'C_federal_entity'];

    function Freecom_partial_construction_servicesDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_partial_construction_services, Free_cfdi, C_federal_entity) {
        var vm = this;
        vm.freecom_partial_construction_services = entity;
        vm.free_cfdis = Free_cfdi.query();
        vm.c_federal_entitys = C_federal_entity.query();
        vm.load = function(id) {
            Freecom_partial_construction_services.get({id : id}, function(result) {
                vm.freecom_partial_construction_services = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_partial_construction_servicesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_partial_construction_services.id !== null) {
                Freecom_partial_construction_services.update(vm.freecom_partial_construction_services, onSaveSuccess, onSaveError);
            } else {
                Freecom_partial_construction_services.save(vm.freecom_partial_construction_services, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
