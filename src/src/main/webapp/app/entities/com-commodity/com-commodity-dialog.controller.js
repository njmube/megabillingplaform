(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_commodityDialogController', Com_commodityDialogController);

    Com_commodityDialogController.$inject = ['$uibModal','$uibModalInstance', 'entity', 'Com_tariff_fraction', 'Com_custom_unit'];

    function Com_commodityDialogController ($uibModal, $uibModalInstance, entity, Com_tariff_fraction, Com_custom_unit) {
        var vm = this;

        vm.com_commodity = entity;
        vm.clear = clear;
        vm.save = save;
        vm.com_tariff_fractions = Com_tariff_fraction.query({pg: -1});
        vm.com_custom_units = Com_custom_unit.query();

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.specific_descriptions = [];

        vm.addComSpecificDescriptions = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-specific-descriptions/com-specific-descriptions-dialog.html',
                controller: 'Com_specific_descriptionsDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            brand: null,
                            model: null,
                            submodel: null,
                            serial_number: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.specific_descriptions.push(result);
            }, function() {
                //do not nothing
            });
        };

        vm.removeComSpecificDescriptions = function(index){
            vm.specific_descriptions.splice(index,1);
        };

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close({
                com_commodity: vm.com_commodity,
                specific_descriptions: vm.specific_descriptions
            });
            vm.isSaving = false;
        }
    }
})();
