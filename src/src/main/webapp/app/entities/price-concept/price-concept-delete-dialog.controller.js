(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Price_conceptDeleteController',Price_conceptDeleteController);

    Price_conceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Price_concept'];

    function Price_conceptDeleteController($uibModalInstance, entity, Price_concept) {
        var vm = this;
        vm.price_concept = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Price_concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
