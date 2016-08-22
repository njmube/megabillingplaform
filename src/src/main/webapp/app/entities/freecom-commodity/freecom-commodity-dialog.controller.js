(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_commodityDialogController', Freecom_commodityDialogController);

    Freecom_commodityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_commodity', 'Freecom_foreign_trade', 'Freecom_tariff_fraction', 'Freecom_custom_unit', 'Freecom_specific_descriptions'];

    function Freecom_commodityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_commodity, Freecom_foreign_trade, Freecom_tariff_fraction, Freecom_custom_unit, Freecom_specific_descriptions) {
        var vm = this;

        vm.freecom_commodity = entity;
        vm.clear = clear;
        vm.save = save;
        vm.freecom_foreign_trades = Freecom_foreign_trade.query();
        vm.freecom_tariff_fractions = Freecom_tariff_fraction.query();
        vm.freecom_custom_units = Freecom_custom_unit.query();
        vm.freecom_specific_descriptions = Freecom_specific_descriptions.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_commodity.id !== null) {
                Freecom_commodity.update(vm.freecom_commodity, onSaveSuccess, onSaveError);
            } else {
                Freecom_commodity.save(vm.freecom_commodity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_commodityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
