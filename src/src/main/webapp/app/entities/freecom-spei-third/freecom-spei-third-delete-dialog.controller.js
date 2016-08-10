(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_spei_thirdDeleteController',Freecom_spei_thirdDeleteController);

    Freecom_spei_thirdDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_spei_third'];

    function Freecom_spei_thirdDeleteController($uibModalInstance, entity, Freecom_spei_third) {
        var vm = this;

        vm.freecom_spei_third = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_spei_third.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
