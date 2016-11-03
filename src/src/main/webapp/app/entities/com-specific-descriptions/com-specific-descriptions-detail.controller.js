(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_specific_descriptionsDetailController', Com_specific_descriptionsDetailController);

    Com_specific_descriptionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_specific_descriptions', 'Com_commodity'];

    function Com_specific_descriptionsDetailController($scope, $rootScope, $stateParams, entity, Com_specific_descriptions, Com_commodity) {
        var vm = this;
        vm.com_specific_descriptions = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_specific_descriptionsUpdate', function(event, result) {
            vm.com_specific_descriptions = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
