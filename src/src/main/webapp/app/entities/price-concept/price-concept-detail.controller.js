(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Price_conceptDetailController', Price_conceptDetailController);

    Price_conceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Price_concept', 'Taxpayer_concept'];

    function Price_conceptDetailController($scope, $rootScope, $stateParams, entity, Price_concept, Taxpayer_concept) {
        var vm = this;
        vm.price_concept = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:price_conceptUpdate', function(event, result) {
            vm.price_concept = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
