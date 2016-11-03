(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_partial_construction_servicesDialogController', Com_partial_construction_servicesDialogController);

    Com_partial_construction_servicesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_partial_construction_services', 'Cfdi', 'C_federal_entity', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Com_partial_construction_servicesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_partial_construction_services, Cfdi, C_federal_entity, C_municipality, C_colony, C_zip_code) {
        var vm = this;
        vm.com_partial_construction_services = entity;
        vm.cfdis = Cfdi.query();
        vm.c_federal_entities = C_federal_entity.query();
        vm.c_municipalities = C_municipality.query();
        vm.c_colonies = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_partial_construction_servicesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_partial_construction_services.id !== null) {
                Com_partial_construction_services.update(vm.com_partial_construction_services, onSaveSuccess, onSaveError);
            } else {
                Com_partial_construction_services.save(vm.com_partial_construction_services, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
