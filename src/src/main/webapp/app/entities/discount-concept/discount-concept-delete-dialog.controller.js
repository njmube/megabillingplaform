(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Discount_conceptDeleteController',Discount_conceptDeleteController);

    Discount_conceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Discount_concept'];

    function Discount_conceptDeleteController($uibModalInstance, entity, Discount_concept) {
        var vm = this;
        vm.discount_concept = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Discount_concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
