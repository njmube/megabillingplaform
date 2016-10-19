(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Discount_conceptController', Discount_conceptController);

    Discount_conceptController.$inject = ['$scope', '$state', 'Discount_concept'];

    function Discount_conceptController ($scope, $state, Discount_concept) {
        var vm = this;
        vm.discount_concepts = [];
        vm.loadAll = function() {
            Discount_concept.query(function(result) {
                vm.discount_concepts = result;
            });
        };

        vm.loadAll();
        
    }
})();
