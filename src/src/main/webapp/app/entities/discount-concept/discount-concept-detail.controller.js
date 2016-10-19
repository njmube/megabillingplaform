(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Discount_conceptDetailController', Discount_conceptDetailController);

    Discount_conceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Discount_concept', 'Taxpayer_concept'];

    function Discount_conceptDetailController($scope, $rootScope, $stateParams, entity, Discount_concept, Taxpayer_concept) {
        var vm = this;
        vm.discount_concept = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:discount_conceptUpdate', function(event, result) {
            vm.discount_concept = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
