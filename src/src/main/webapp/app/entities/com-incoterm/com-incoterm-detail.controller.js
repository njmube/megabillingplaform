(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_incotermDetailController', Com_incotermDetailController);

    Com_incotermDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_incoterm'];

    function Com_incotermDetailController($scope, $rootScope, $stateParams, entity, Com_incoterm) {
        var vm = this;
        vm.com_incoterm = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_incotermUpdate', function(event, result) {
            vm.com_incoterm = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
