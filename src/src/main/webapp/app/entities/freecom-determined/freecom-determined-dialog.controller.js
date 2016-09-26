(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_determinedDialogController', Freecom_determinedDialogController);

    Freecom_determinedDialogController.$inject = ['$uibModalInstance', 'entity', 'Rate_type','Freecom_tax_type'];

    function Freecom_determinedDialogController ($uibModalInstance, entity, Rate_type, Freecom_tax_type) {
        var vm = this;
        vm.freecom_determined = entity;
        vm.rate_type = null;
        vm.rate_typess = Rate_type.query({filtername: " "});
        vm.freecom_tax_types = Freecom_tax_type.query();

        vm.onRateTypeChange = function(){
            if(vm.rate_type != null){
                vm.freecom_determined.rate = vm.rate_type.value;
            }
            else{
                vm.freecom_determined.rate = null;
            }
        };

        vm.save = function () {
            vm.isSaving = true;

            $uibModalInstance.close(vm.freecom_determined);

            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
