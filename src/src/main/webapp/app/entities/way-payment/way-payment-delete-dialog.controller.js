(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Way_paymentDeleteController',Way_paymentDeleteController);

    Way_paymentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Way_payment'];

    function Way_paymentDeleteController($uibModalInstance, entity, Way_payment) {
        var vm = this;

        vm.way_payment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Way_payment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
