(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Price_conceptController', Price_conceptController);

    Price_conceptController.$inject = ['$scope', '$state', 'Price_concept'];

    function Price_conceptController ($scope, $state, Price_concept) {
        var vm = this;
        vm.price_concepts = [];
        vm.loadAll = function() {
            Price_concept.query(function(result) {
                vm.price_concepts = result;
            });
        };

        vm.loadAll();
        
    }
})();
