(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_determinedDialogController', Com_determinedDialogController);

    Com_determinedDialogController.$inject = ['$uibModalInstance', 'entity', 'Rate_type','Freecom_tax_type'];

    function Com_determinedDialogController ($uibModalInstance, entity, Rate_type, Freecom_tax_type) {
        var vm = this;
        vm.com_determined = entity;
        vm.rate_type = null;
        vm.rate_typess = Rate_type.query({filtername: " "});
        vm.freecom_tax_types = Freecom_tax_type.query();

        vm.onRateTypeChange = function(){
            if(vm.rate_type != null){
                vm.com_determined.rate = vm.rate_type.value;
            }
            else{
                vm.com_determined.rate = null;
            }
        };

        vm.save = function () {
            vm.isSaving = true;

            $uibModalInstance.close(vm.com_determined);

            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
